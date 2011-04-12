$.generateId = function() {
    return arguments.callee.prefix + arguments.callee.count++;
};
$.generateId.prefix = 'jq$';
$.generateId.count = 0;
$.fn.generateId = function() {
    return this.each(function() {
        this.id = $.generateId();
    });
};


/*
 * @author: igor
 */
 
(function($){

	/** forwards function invocation to fullCalendar plugin */
	function forward(element, args) {
		return $.fn.fullCalendar.apply($(element), args);
	}

	function copyArray(original) {
		var copy=[];
		for (var i=0,len=original.length;i<len;i++)
			copy.push(original[i]);
		return copy;
	}
	
    $.fn.fullCalendarExt = function(method){
    
    	var _arguments=copyArray(arguments);
	  	if (typeof method == 'string') {
			var args = Array.prototype.slice.call(_arguments, 1);
			var res;
			this.each(function() {
				var instance = $.data(this, 'fullCalendarExt');
				if (instance && $.isFunction(instance[method])) {
					
					var r = instance[method].apply(instance, args);
					if (res === undefined) {
						res = r;
					}
					if (method == 'destroy') {
						$.removeData(this, 'fullCalendarExt');
						forward(this, _arguments);
					}
				} else {
					res=forward(this, _arguments);
				}
			});
			if (res !== undefined) {
				return res;
			}
			return this;
		}
		

		this.each(function() {
			
			var ext=(function(owner) {
				var options=method;
				
				
				var sources=options.eventSources||[];
				sources=copyArray(sources);
				
				$(sources).each(function() {
					if (typeof(this.data.fcxEnabled)==="undefined") {
						this.data.fcxEnabled=true;
					}
				});
				
				
				function findSource(uuid) {
					for (var i=0,len=sources.length;i<len;i++) {
						if (sources[i].data.fcxUuid===uuid) return sources[i];
					}
				}
				
				function toggleSource(owner, uuid, val) {
					var source=findSource(uuid);
					val=val||!source.data.fcxEnabled;
					if (val&&!source.data.fcxEnabled) {
						$(owner).fullCalendar('addEventSource', source);
						source.data.fcxEnabled=true;
					} else if (!val&&source.data.fcxEnabled) {
						$(owner).fullCalendar('removeEventSource', source);
						source.data.fcxEnabled=false;
					}
				}
				
				
				var ext={};
			
				ext.createEventSourceSelector=function(id) {
					var ul=$("#"+id);
					$(sources).each(function() {
						var sourceUuid=this.data.fcxUuid||null;
						
						var checkbox=$("<input type='checkbox'/>");
						if (this.data.fcxEnabled) { checkbox.attr("checked","checked"); }
						checkbox.bind("click", function() { toggleSource(owner, sourceUuid, this.checked); });
						checkbox.generateId();
						var li=$("<li></li>");
						checkbox.appendTo(li);
						$("<label for='"+checkbox.attr("id")+"'>"+this.data.fcxTitle+"</label>").appendTo(li);
						li.appendTo(ul);					
					});
				}
			
				return ext;
			}(this));
			
			$.data(this, 'fullCalendarExt', ext);
			
			forward(this, _arguments);
		});

		
		
		
    };
    
})(jQuery);