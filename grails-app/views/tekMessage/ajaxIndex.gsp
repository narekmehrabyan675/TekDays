<!DOCTYPE html>
<html>
<head>
%{--
    <g:javascript library="jquery" />
--}%
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'tekMessage.label',
            default: 'TekMessage')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-tekMessage" class="skip" tabindex="-1"><g:message
        code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message
                code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"
                    params='["event.id":"${event?.id}"]'><g:message
                    code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>

<div id="list-tekMessage" class="content scaffold-list" role="main">
    <h1>${event?.name} - Forum Messages</h1>
    <div id="messageList">
        <g:messageThread messages="${tekMessageInstanceList}"/>
       %{-- <g:each in="${tekMessageInstanceList}" var="tekMessageInstance">
            <g:remoteLink action="showDetail" id="${tekMessageInstance?.id}"
                           update="details">
                 <p style="margin-left: 25px;">${tekMessageInstance.author.fullName} - ${tekMessageInstance.subject}</p>
                 </g:remoteLink>
        </g:each>--}%
    </div>
    <h3 style="margin-left: 25px;">Message Details</h3>
    <div id="details">
    </div>
</div>
</body>
</html>