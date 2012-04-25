function(start, end, callback) {
    $$.ajax({
        url: '${url}',
        dataType: 'json',
        data: {
            start: Math.round(start.getTime()),
            end: Math.round(end.getTime()),
            timezoneOffset: new Date().getTimezoneOffset(),
            anticache: ""+new Date().getTime()+"."+Math.random()
        },
        success: function(events) {
            callback(events);
        }
    });
}