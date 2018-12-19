FROM openjdk:8-jdk-alpine
LABEL maintainer="christoph.birk@gmail.com" \
      application="jenkins-dashboard"
ADD target/jenkins-dashboard-latest.jar app.jar
ENV JAVA_OPTS=""
ENV SPRING_APPLICATION_JSON=\
'{ "baseUrl": "https://jenkins.mono-project.com/view/All/", "url": {"searchPattern": "", "replacement": "" },\
    "credits": null, "jiraBaseUrl": null, "jiraTaskRegEx": "TBBT-\\d{1,4}", \
     "swimlanes": { "Mainline": "^(master).*", "Features": "^(feature).*" } }'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]