FROM openjdk:8-jre-alpine
WORKDIR /coffee-editor
COPY ./launch.sh /coffee-editor
COPY ./java/org.eclipse.emfcloud.coffee.example.k8s.product/target/products/org.eclipse.emfcloud.coffee.example.k8s.product/linux/gtk/x86_64 /coffee-editor
CMD ./launch.sh
