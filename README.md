
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

#Remove seleniumn and Tess4J:
1, rename bathsbc-trim.txt to bathsbc-trim.bat
2, run bathsbc-trim.bat

Questionnaire sample:
--------------------------------------------------------------------------------------
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<form action="/api/qn/poll/b/resp" method="POST" class="form-horizontal">
				<div class="control-group">
					<label class="control-label" for="inputEmail">邮箱</label>
					<div class="controls">
						<input id="inputEmail" name="inputEmail" type="text" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="inputPassword">密码</label>
					<div class="controls">
						<input id="inputPassword" name="inputPassword" type="password" />
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<label class="checkbox"><input type="checkbox" /> Remember me</label> <button class="btn" type="submit">登陆</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
--------------------------------------------------------------------------------------

