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
tondeuse-batch/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── tondeuse/
│   │   │           ├─TondeuseBatchApplication.java
│   │   │           ├── batch/config/
│   │   │           │   └── BatchConfig.java
│   │   │           ├── model/
│   │   │           │   └─Tondeuse.java
│   │   │           │   └─ Pelouse.java
│   │   │           |
|   |   |           ├── batch/processor/
│   │   │           │   └─ TondeuseItemProcessor.java
│   │   │           ├── batch/reader/
│   │   │           │   └─TondeuseItemReader.java
│   │   │           ├── batch/writer/
│   │   │           │   └─TondeuseItemWriter.java
│   ├── test/
│       ├── java/
│       │   └── com/
│       │       └── tondeuse/
│       │           ├─TondeuseBatchIntegrationTest.java
|       |           ├─TondeuseBatchApplicationTest.java
│   │   │           ├── batch/processor/
│   │   │           │   └ TondeuseItemProcessorTest.java
│   │   │           ├── batch/reader/
│   │   │           │   └TondeuseItemReaderTest.java
│   │   │           ├── batch/writer/
│   │   │           │   └TondeuseItemWriterTest.java
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
git clone https://github.com/Maazouz-Zouhair/tondeuse-batch.git
cd tondeuse-batch
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

## Déploiement avec Docker (locale)
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
## Déploiement avec Jenkins (locale)
### I.  Créer un Nouveau Job Pipeline
* Allez dans `Jenkins` > `New Item`.
* Entrez un nom pour le job, sélectionnez `Pipeline`, puis cliquez sur `OK`
### II. Configurer le Pipeline:
- Dans la section Pipeline, sélectionnez Pipeline script from SCM.
- SCM: Git
- Repository URL: https://github.com/Maazouz-Zouhair/tondeuse-batch.
- Branch Specifier: `main`.
- Script Path: Le chemin vers le fichier Jenkinsfile.
#### Jenkinsfile pour le Déploiement Local
```
pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'tondeusebatch'
    }

    stages {
        stage('Checkout') {
            steps {
                // Cloner le dépôt
                git branch: 'main', url: 'https://github.com/Maazouz-Zouhair/tondeuse-batch'
            }
        }

        stage('Build') {
            steps {
                // Construire l'application avec Maven
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Construire l'image Docker
                    docker.build("${env.DOCKER_IMAGE}:${env.BUILD_ID}")
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Arrêter et supprimer les anciens conteneurs
                    sh """
                    docker stop tondeusebatch || true
                    docker rm 
                    tondeusebatch || true
                    """

                    // Déployer le nouveau conteneur Docker
                    sh """
                    docker run -d --name tondeusebatch -p 8080:8080 ${env.DOCKER_IMAGE}:${env.BUILD_ID}
                    """
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline terminé'
        }
        success {
            echo 'Le déploiement a été effectué avec succès!'
        }
        failure {
            echo 'Le déploiement a échoué.'
        }
    }
}
```
### III. Exécuter le Pipeline
#### 1. Enregistrer et Construire le Job:
- Cliquez sur Save pour enregistrer le job.
- Cliquez sur Build Now pour exécuter le pipeline.
