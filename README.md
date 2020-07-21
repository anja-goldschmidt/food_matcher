# food_matcher
My first project, a prototype for matching left-over food to demands in order to reduce waste. Created as the final assignment in the IT-training programme at Sogyo (sogyo.nl).

This is a Maven project that runs in Springboot with a MySQL database. Communication with the database is regulated via Hibernate. The client-side is rendered with vue.js.

If you want to run this project on your machine after cloning, set up a MySQL database that includes tables for user, address, offer and demand (see corresponding java classes in model for required columns). To start the server run the command "mvn spring-boot:run" (without the quotation marks) in the main folder.
