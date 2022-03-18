
Swagger URL: <a>http://localhost:8080/springdoc/swagger-ui/index.html?configUrl=/springdoc/api-docs/swagger-config

<pre>
This config only limit the file upload size, 
has no limitation effect on post Json body. i.e. @RequestBody
spring:
    servlet:
        multipart:
            max-file-size: 1MB
            max-request-size: 1MB
</pre>




