<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Activation</title>
</head>
<body>
<h2>${message}</h2>

<g:if test="${flash.message}">
    <div style="margin: 20px">${flash.message}</div>
</g:if>

<g:if test="${flash.errorMessage}">
    <div style="margin: 20px;  color: red;" >${flash.errorMessage}</div>
</g:if>


<g:form  action="activate" method="POST">
    <label for="code">Write code of activation:</label>
    <input type="hidden" name="redirectUrl" value="${redirectUrl}">

    <g:textField name="code" id="code"/>
    <br/>
    <g:submitButton name="submit" value="Activate"/>
</g:form>
</body>
</html>
