FROM node:10-buster

# Install Java and update
RUN apt-get update && apt-get install -y default-jdk libsecret-1-dev xvfb libx11-dev libxkbfile-dev maven libxml2-utils && apt-get upgrade -y

WORKDIR /coffee-editor

RUN useradd -ms /bin/bash theia

COPY --chown=theia:theia . /coffee-editor

# Set location to place global npm dependencies
ENV NPM_CONFIG_PREFIX=/home/node/.npm-global

# Expose port
EXPOSE 3000

USER theia

RUN /coffee-editor/run.sh -f && \
	wget http://35.246.187.143/favicon.ico -P /coffee-editor/web/browser-app/lib && \
	sed -i 's/<\/head>/<link rel="icon" href="favicon.ico" \/><\/head>/g' /coffee-editor/web/browser-app/lib/index.html

CMD cd /coffee-editor/web/browser-app && yarn start --hostname 0.0.0.0
