# FivvyRepoChallenge

This project is a demonstration application for Fivvy Prject. It provides basic functionality for managing disclaimers.

## Prerequisites

- Java 17
- SpringBoot 3.x

## Configuration

1. Create and Clone repository 

`C:\> mkdir my_directory\n`

`C:\> cd my_directory\n`

`C:\my_directory> git clone https://github.com/josetucuman/FivvyRepoChallenge.git`



2. Navigate to the project directory.

`C:\my_directory> cd FivvyRepoChallenge`

`C:\my_directory\FivvyRepoChallenge>`

3. Compile and Execute

`mvn spring-boot:run`

This app run on `localhost:9090`

## Usage
### Create a new Disclaimer
Endpoint: POST /disclaimers
Input parameters: A DisclaimerDto object in JSON format with the fields name, text, and version.
Example request:

{

  "name": "Example Disclaimer",
  
  "text": "This is an example disclaimer.",
  
  "version": "1.0"
  
}
### Deleting an existent Disclaimer

- Endpoint `/disclaimers/{id}`
- Parameter: ID Disclaimer

This application use H2 Database. 

