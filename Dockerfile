# Setup dev environment
FROM ubuntu:18.04 AS environment

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update && \
	apt-get upgrade -y && \
	apt-get install -y default-jdk maven && \
	apt-get install wget build-essential cmake libopenblas-dev gnupg curl make git g++-multilib clangd-10 gdb libsecret-1-dev -y

RUN update-alternatives --install /usr/bin/clangd clangd /usr/bin/clangd-10 100

RUN curl -fsSL https://deb.nodesource.com/setup_16.x | bash - && \
	apt-get install nodejs -y && \
	npm install -g yarn

# Build the Java backend
FROM environment AS backend

WORKDIR /coffee-editor

COPY ./backend ./backend

WORKDIR /coffee-editor/backend/releng/org.eclipse.emfcloud.coffee.parent/

RUN mvn clean verify


# Build frontend
FROM environment AS frontend

WORKDIR /coffee-editor

COPY ./client ./client

WORKDIR /coffee-editor/client

RUN yarn install
RUN yarn production


# Combine frontend and backend
FROM environment AS application

# Make readable for root only
RUN chmod -R 750 /var/run/

# Create a user to not run the container as root
RUN useradd -ms /bin/bash theia
WORKDIR /coffee-editor
COPY --chown=theia:theia --from=frontend /coffee-editor/client ./client
COPY --chown=theia:theia --from=backend /coffee-editor/backend/ ./backend

WORKDIR /coffee-editor/client
RUN yarn copy:servers
WORKDIR /coffee-editor

# Copy favicon
RUN cp ./client/favicon.ico ./client/browser-app/lib
RUN sed -i 's/<\/head>/<link rel="icon" href="favicon.ico" \/><\/head>/g' client/browser-app/lib/index.html

USER theia

# Set up git repo in workspace for comparison
WORKDIR /coffee-editor/client/workspace/SuperBrewer3000
RUN git config --global user.name "Test User"
RUN git config --global user.email "test@example.com"
RUN git init
RUN git add *
RUN git commit -m "init"

# Start the browser-app
WORKDIR /coffee-editor/client/browser-app
EXPOSE 3000
CMD yarn start --hostname 0.0.0.0
