
##MERCEDES CAR SIMULATION

Project purpose is that implement an application accessing Auth. API take information and display in web page.
We will show the last updated information on the web page.When data changed on API, we must to trigger FE.
We also change information on Auth. API with post request.
Of course each application has trade-off. Even if, i explain tech solution in briefly on this app with this requests.

>Never shoot for best arcitecture ,but rather the least worst

I prefer [WebSocket](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API) so that communication between frontend and backend could be provided bidirectionally.
I ensured that incoming message on WebSocket was triggered by [Events Emitter](https://www.npmjs.com/package/events)
I decide my architecture is monolith layered architecture so one docker file enough to be dockerize.
So i implement [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin) in my project.
This provided me frontend distinguish backend independent as possible according to my architecture.
At the same time , use Node.js and its libraries in your build process without installing Node/NPM globally for your build system.

Use effective Js library as **React** that provide us single page application ease higher performance and reusable components
For CSS template [Bootstrap](https://getbootstrap.com/)

For backend ,I develop application on **Spring framework** with **Java**. I send the request Auth API with [Rest Template](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)
I check access-token expire case when take Http Auth Error.

## Building and starting the backend

Go in the project root folder and run the following:

mvn clean spring-boot:run

This will start a backend listening on port 8080 so make sure port 8080 is available


## Starting the frontend
Next go in the project subfolder frontend and run the following:

npm install
npm start

if any problem on web page , clean the cache or open application with indigo page

