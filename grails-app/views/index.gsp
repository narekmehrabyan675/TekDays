<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
   %{-- <style>
    body, html {
        width: 100% !important;
        max-width: 100% !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    </style>--}%


    <meta name="layout" content="main"/>
    <title>TekDays - The Community is the Conference!</title>
</head>

<body>
<div style=" margin: 0 20px 0 20px">
<div id="welcome">
    <br/>

    <h3> <g:message code = "welcome.head"></g:message>  </h3>

    <p style="margin-top: 20px">
        <g:message code="welcome.title1"></g:message>
    </p>
</div>

%{--<div id="homeSearch">
	<g:form controller="Searchable" action="updatedHQL">
		<label>Search:</label>
		<input id="query" type="text" name="query" />
		<input type=submit value="Go" />
	</g:form>
</div>--}%

<button type="button" class="btn btn-primary openModalBtn" data-toggle="modal" data-target="#myModal">
    <g:message code="button.search"></g:message>
</button>
<!-- Modal view page-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Search HQL</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body" style="height: 600px; padding: 0;">
                <iframe id="modalIframe" src="" style="width: 100%; height: 100%; border: none;"></iframe>
            </div>
        </div>
    </div>
</div>
    <style>
    .custom-size {
        max-width: 95%;
        width: 1000px;
        height: 800px
    }

    .modal-body {
        height: 800px ;
        padding: 0;
    }
    </style>

    <g:organizerEvents/>
<g:volunteerEvents/>
<div class="homeCell">
    <h3><g:message code="welcome.title7"></g:message> </h3>

    <p>        <g:message code="welcome.title2"></g:message>
    </p>
    <span class="buttons">
        <g:link controller="tekEvent" action="index"><g:message code="welcome.title7"/></g:link>
    </span>
</div>

<div class="homeCell">
    <h3> <g:message code="welcome.title8"/></h3>

    <p>        <g:message code="welcome.title3"/>
    </p>
    <span class="buttons">
        <g:link controller="tekEvent" action="create"><g:message code="welcome.title8"/></g:link>
    </span>
</div>

<div class="homeCell">
    <h3><g:message code="welcome.title9"></g:message> </h3>

    <p>        <g:message code="welcome.title4"></g:message>
   </p>
    <span class="buttons">
        <g:link controller="sponsor" action="create"><g:message code="welcome.title9"></g:message> </g:link>
    </span>
</div>
</div>


<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    var currentId = null;

    $('#myModal').on('show.bs.modal', function () {

        $('.openModalBtn').on('click', function () {
            $('#myModal').modal({
                backdrop: false,
                keyboard: true
            });
        });

        const host = window.location.hostname;
        $('#modalIframe').attr('src', 'http://' + host + ':9090/TekDays/searchable/updatedHQL');
    });

    $('#myModal').on('hidden.bs.modal', function () {
        $('#modalIframe').attr('src', '');
    });
</script>


</body>
</html>

