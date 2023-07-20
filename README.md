# Instructions for a Simple Java App using Hibernate + JPA and Spark Framework:

## Setup:
1. Make sure you have Gradle installed on your system.
2. Clone the project or create a new directory for your Java app.
3. Navigate to the project directory.

## Create a PostgreSQL Database:
1. Open your preferred PostgreSQL client or use the command line to create a new database. For example:

```sql
CREATE DATABASE java_demo_app;
```
## Configure Flyway and Persistence:
1. Open the build.gradle file and update the flyway section with your PostgreSQL database settings (URL, user, and password).
2. Update the settings in src/main/resources/META-INF/persistence.xml to match your database configuration. 

## Execute Database Migrations
1. Open a terminal or command prompt and run the following command to apply the database migrations:
> $ gradle flywayMigrate

## Start the App

1. In the terminal or command prompt, run the following command to start the app:
> $ gradle run

Interact with the API
1. Once the app is running, you can use a tool like "requests.http" to send HTTP requests and interact with the API endpoints.
2. Open the "requests.http" file and use it to test various API endpoints and operations.

Note: Make sure to replace the database settings, such as URL, username, and password, with your actual database credentials. Additionally, ensure that the necessary dependencies and configurations are correctly set up in your build.gradle file for Hibernate, JPA, and Spark.