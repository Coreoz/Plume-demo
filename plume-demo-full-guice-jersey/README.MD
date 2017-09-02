A demo application of the whole Plume ecosystem
===============================================

Running the application
-----------------------
Just launch the `org.plume.demo.WebApplication` class and open <http://localhost:8080/> in your browser.

If it is not already the case, you will also have to configure your IDE to support Lombok:
<https://projectlombok.org/>

Administration area
-------------------

Open <http://localhost:8080/admin/> in your browser. In the login screen, the credentials are:
- login: admin
- password: admin

Swagger documentation
---------------------
It is available through the URL:
- for the public area: <http://localhost:8080/webjars/swagger-ui/2.2.10-1/index.html?url=/api/swagger>
- for the admin area: <http://localhost:8080/webjars/swagger-ui/2.2.10-1/index.html?url=/api/swaggerAdmin>

Then the browser should prompt for credentials:
- login: plume
- password: rocks

To test an administration web-service like `GET /admin/city`:
1. Call the `POST /admin/session` web-service with the credentials admin//admin
2. Copy the JWT token returned
3. At the top of the Swagger page, click on the Authorize button
4. Type in the popin: `Bearer <the copied token from step 2>` and hit the Authorize button
5. Your done, you can call any web-service that require an administration access!


