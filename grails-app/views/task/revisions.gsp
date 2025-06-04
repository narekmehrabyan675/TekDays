<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Task Revisions</title>
</head>
<body>
<h1>Revisions for Task ID: ${params.id}</h1>

<ul>
    <g:each in="${revisionList}" var="rev">
        <li>
            Revision #: ${rev[1].id}, Date: ${new Date(rev[1].timestamp)}
            <br/>

            Changed By: ${rev[1].currentUser?.userName ?: 'Unknown'}
            <br/>
            Task Title: ${rev[0].toString()}
        </li>
    </g:each>
</ul>
</body>
</html>
