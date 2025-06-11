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
<div style="left: 50%; top:50%">
<div id="welcome">
    <br/>

    <h3>Welcome to TekDays.com</h3>

    <p>TekDays.com is a site dedicated to assisting individuals and
    communities to organize technology conferences. To bring great
    minds with common interests and passions together for the good
    of greater geekdom!</p>
</div>

%{--<div id="homeSearch">
	<g:form controller="Searchable" action="updatedHQL">
		<label>Search:</label>
		<input id="query" type="text" name="query" />
		<input type=submit value="Go" />
	</g:form>
</div>--}%

<button type="button" class="btn btn-primary openModalBtn" data-toggle="modal" data-target="#myModal">
    Search1
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
    <h3>Find a Tek Event</h3>

    <p>See if there's a technical event in the works that strikes your
    fancy. If there is, you can volunteer to help or just let the
    organizers know that you'd be interested in attending.
    Everybody has a role to play.</p>
    <span class="buttons">
        <g:link controller="tekEvent" action="index">Find a Tek Event</g:link>
    </span>
</div>

<div class="homeCell">
    <h3>Organize a Tek Event</h3>

    <p>If you don't see anything that suits your interest and location,
    then why not get the ball rolling. It's easy to get started and
    there may be others out there ready to get behind you to make it
    happen.</p>
    <span class="buttons">
        <g:link controller="tekEvent" action="create">Organize a Tek Event</g:link>
    </span>
</div>

<div class="homeCell">
    <h3>Sponsor a Tek Event</h3>

    <p>If you are part of a business or organization that is involved in
    technology then sponsoring a tek event would be a great way to
    let the community know that you're there and you're involved.</p>
    <span class="buttons">
        <g:link controller="sponsor" action="create">Sponsor a Tek Event</g:link>
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

