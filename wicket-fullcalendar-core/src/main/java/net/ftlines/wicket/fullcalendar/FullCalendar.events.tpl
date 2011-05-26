function(start, end, callback) {
    $$.ajax({
        url: '${url}',
        dataType: 'json',
        data: {
            start: Math.round(start.getTime()),
            end: Math.round(end.getTime())
        },
        headers: {
        	"Wicket-Ajax": true
        },
        success: function(events) {
            callback(events);
        }
    });
}