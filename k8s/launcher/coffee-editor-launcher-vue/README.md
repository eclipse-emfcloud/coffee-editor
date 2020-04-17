# coffee-editor-launcher-vue

## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn serve
```

### Compiles and minifies for production
```
yarn build
```

### Lints and fixes files
```
yarn lint
```

### Build docker image
```
docker build -t coffee-editor-vue .

docker run -it -p 8080:80 --rm --name coffee-editor-vue coffee-editor-vue
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
