FROM ubuntu:latest AS cpp-theia-base

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update && \
	apt-get upgrade -y && \
	apt-get install -y default-jdk maven && \
	apt-get install wget build-essential cmake libopenblas-dev gnupg curl make git g++-multilib clangd-10 gdb -y

RUN update-alternatives --install /usr/bin/clangd clangd /usr/bin/clangd-10 100

RUN curl -fsSL https://deb.nodesource.com/setup_12.x | bash - && \
	apt-get install nodejs -y && \
	npm install -g yarn

WORKDIR /usr/coffee-editor
COPY ./backend/examples/SuperBrewer3000 ./backend/examples/SuperBrewer3000

COPY . .
RUN ./run.sh -bcf && \
	cp ./web/favicon.ico ./web/browser-app/lib
RUN sed -i 's/<\/head>/<link rel="icon" href="favicon.ico" \/><\/head>/g' web/browser-app/lib/index.html

WORKDIR /usr/coffee-editor/web/browser-app

EXPOSE 3000
CMD yarn start --hostname 0.0.0.0