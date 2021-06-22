## MERCEDES CAR SIMULATION

Project purpose is to implement an application for accessing Auth. API to take information and to display in web page.
I will show the last updated information on the web page. I must to trigger frontend, when data changes on API.
I; moreover, change information on Auth. API with post request.

I explain my technological solution in briefly on this app with specifications, even if each application has trade-off in nature.

>Never shoot for best arcitecture ,but rather the least worst

I prefer [WebSocket](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API) so that communication between frontend and backend could be provided bidirectionally.
I have ensured that incoming message on WebSocket is triggered by [Events Emitter](https://www.npmjs.com/package/events)
I decide that my architecture is monolith layered architecture so one docker file enough to be dockerized.
So I implement [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin) in my project.
This provides me that frontend distinguishes backend independent as possible according to my architecture.
At the same time, to use Node.js and its libraries in build process without installing Node/NPM globally for build system.

To use effective Js library as **React**, which provides single page application, offers higher performance and reusable components
I choose  [Bootstrap](https://getbootstrap.com/) for CSS template.

I develop application on **Spring framework** with **Java** software language for backend. I send requests Auth API with [Rest Template](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)
I check access-token whether it is expired or not, when Http Auth Error is appeared.

## Building and starting the backend

Go in the project root folder and run the following:

mvn clean spring-boot:run

This will start a backend listening on port 8080 so make sure port 8080 is available.


## Starting the frontend
Next go in the project subfolder frontend and run the following:

npm install
npm start

if there is any problem on web page, clean the cache or open application with incognito mod page

## Owner contact
Email : [asli.bhr.apaydin@gmail.com](mailto:asli.bhr.apaydin@gmail.com)
LinkedIn: [aslibaharcay](https://www.linkedin.com/in/asl%C4%B1-bahar-%C3%A7ay-7b0b7779/)
