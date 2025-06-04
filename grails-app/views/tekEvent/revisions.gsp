<h1>Revisions for TekEvent ID: ${params.id}</h1>

<ul>
    <g:each in="${revisionList}" var="rev">
        <li>
            Revision #: ${rev[1].id}, Date: ${new Date(rev[1].timestamp)},
            Changed By: ${rev[1].currentUser?.userName ?: 'Unknown'}
            <br/>
            Event Title: ${rev[0].toString()}
        </li>
    </g:each>
</ul>
