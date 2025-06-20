

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="revlay">
    <g:set var="entityName" value="${message(code: 'book.label', default: 'Book')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- DataTables -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

    <script>
        $(document).ready(function() {
            $('#revisionTable').DataTable({
                searching: true,
                pageLength: 10,
                order: [[0, 'desc']]
            });
        });
    </script>

    <r:layoutResources />

</head>
<body>

<table id="revisionTable" class="display">
    <thead>
    <tr>
        <th><g:message code="tekEvent.revision.label" default="Description"/> #</th>
        <th><g:message code="tekEvent.date.label" default="Date"/></th>
        <th><g:message code="tekEvent.changedby.label" default="Changed By"/></th>
        <th><g:message code="tekEvent.eventtitle.label" default="Event Title"/></th>
        <th><g:message code="tekEvent.city.label" default="City"/></th>
        <th><g:message code="tekEvent.description.label" default="Description"/></th>
        <th><g:message code="tekEvent.venue.label" default="Venue"/></th>

    </tr>
    </thead>
    <tbody>
    <g:each in="${revisionList}" var="rev">
        <tr>
            <td>${rev[1].id}</td>
            <td>  <g:formatDate date="${new Date(rev[1].timestamp)}" format="yyyy-MM-dd HH:mm:ss"/>%{--${new Date(rev[1].timestamp)}--}%</td>
            <td>${rev[1].currentUser?.userName ?: 'Unknown'}</td>
            <td>${rev[0].name}</td>
            <td>${rev[0].city}</td>
            <td>${rev[0].description}</td>
            <td> ${rev[0].venue}</td>
        </tr>
    </g:each>
    </tbody>
</table>

<r:layoutResources name="defer"/>
</body>
</html>



