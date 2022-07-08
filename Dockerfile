FROM eclipse-temurin:11-alpine
RUN mkdir -p /opt/app /opt/app/libs
COPY ./build/dependentLibs/*.jar /opt/app/libs
COPY ./build/libs/joi-1.0-plain.jar /opt/app/app.jar
CMD ["java", "-classpath", "/opt/app/libs/*:/opt/app/app.jar", "uk.tw.energy.App"]


