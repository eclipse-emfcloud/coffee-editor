FROM node:10-buster

# Install Java and update
RUN apt-get update && apt-get install -y default-jdk libsecret-1-dev xvfb libx11-dev libxkbfile-dev maven libxml2-utils && apt-get upgrade -y

# Make readable for root only
RUN chmod -R 750 /var/run/

WORKDIR /coffee-editor

RUN useradd -ms /bin/bash theia

COPY --chown=theia:theia . /coffee-editor

# Set location to place global npm dependencies
ENV NPM_CONFIG_PREFIX=/home/node/.npm-global

# Expose port
EXPOSE 3000

USER theia

RUN /coffee-editor/run.sh -f && \
	mkdir web/browser-app/plugins && \
	cd /coffee-editor/backend/examples/SuperBrewer3000 && \
	mvn clean verify && \
	rm -r target && \
	cd /coffee-editor/web/browser-app/plugins && \
	wget https://github.com/theia-ide/vscode-builtin-extensions/releases/download/v1.39.1-prel/java-1.39.1-prel.vsix && \
	wget https://download.jboss.org/jbosstools/static/jdt.ls/stable/java-0.50.0-1825.vsix && \
	wget https://github.com/microsoft/vscode-java-debug/releases/download/0.22.0/vscode-java-debug-0.22.0.vsix && \
	cp /coffee-editor/web/favicon.ico /coffee-editor/web/browser-app/lib && \
	sed -i 's/<\/head>/<link rel="icon" href="favicon.ico" \/><\/head>/g' /coffee-editor/web/browser-app/lib/index.html

CMD cd /coffee-editor/web/browser-app && sleep 5 && yarn start --hostname 0.0.0.0
