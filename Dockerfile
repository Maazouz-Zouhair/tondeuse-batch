# Utiliser une image de base avec JDK 11
FROM openjdk:11-jre-slim

# Créer un volume pour les fichiers temporaires
VOLUME /tmp

# Copier le fichier JAR généré dans l'image Docker
ARG JAR_FILE=target/tondeuse-batch-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Exposer le port 8080
EXPOSE 8080

# Définir le point d'entrée pour exécuter l'application
ENTRYPOINT ["java","-jar","/app.jar"]
