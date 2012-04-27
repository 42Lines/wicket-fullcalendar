function(start, end, callback) {

    Wicket.Ajax.ajax({
        'u': '${url}',
        'dt': 'json',
        'dep': {
            start: Math.round(start.getTime()),
            end: Math.round(end.getTime()),
            timezoneOffset: new Date().getTimezoneOffset(),
            anticache: ""+new Date().getTime()+"."+Math.random()
        },
        'sh': function(events) {
            callback(events);
        }
    });
}