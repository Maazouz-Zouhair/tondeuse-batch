# Simulateur de Tondeuse

## Vue d'ensemble

Ce projet simule le fonctionnement d'une tondeuse à gazon automatique qui navigue sur une pelouse rectangulaire en fonction d'un ensemble d'instructions. La position et l'orientation de la tondeuse sont suivies, et les instructions guident ses mouvements. Le projet utilise Spring Batch pour traiter les instructions de la tondeuse à partir d'un fichier d'entrée.

## Fonctionnalités

- Dimensions de pelouse configurables et instructions de tondeuse
- Prend en charge plusieurs tondeuses
- Lecture des entrées à partir d'un fichier
- Traitement des mouvements de la tondeuse avec Spring Batch
- Affiche les positions et orientations finales des tondeuses

## Structure du projet

```plaintext
mower-simulator/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── mower/
│   │   │           ├── MowerSimulatorApplication.java
│   │   │           ├── config/
│   │   │           │   └── BatchConfig.java
│   │   │           ├── model/
│   │   │           │   ├── Mower.java
│   │   │           │   ├── Lawn.java
│   │   │           ├── processor/
│   │   │           │   └── MowerItemProcessor.java
│   │   │           ├── reader/
│   │   │           │   └── MowerItemReader.java
│   │   │           ├── writer/
│   │   │           │   └── MowerItemWriter.java
│   ├── test/
│       ├── java/
│       │   └── com/
│       │       └── mower/
│       │           ├── MowerBatchIntegrationTest.java
│       └── resources/
│           ├── application-test.properties
│           └── input/
│               └── input.txt
└── pom.xml
```
## Prise en main
### Prérequis
- Java 11 ou plus récent
- Maven 3.6.0 ou plus récent

## Installation
1. Cloner le dépôt :
```
git clone https://github.com/votre-nom-utilisateurmower-simulator.git
cd mower-simulator
```
2. Construire le projet :
```
mvn clean install
```
## Configuration 
`application.properties`

Le fichier de configuration principal de l'application situé dans src/main/resources/application.properties.

`application-test.properties`
Le fichier de configuration de test situé dans src/test/resources/application-test.properties.

## Déploiement avec Docker
### Création d'un Dockerfile: 
```
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
```
### Construire l'image Docker:

```
docker build --pull --rm -f "Dockerfile" -t tondeusebatch "."
```
### Exécuter le conteneur Docker:
```
docker run -p 8080:8080 tondeusebatch
```