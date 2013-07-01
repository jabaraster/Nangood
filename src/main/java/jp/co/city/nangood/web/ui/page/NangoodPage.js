(function() {
	var counter = 0;
	var ary = [];
    function initialize() {
    	$('#nangood').click(function() {
    		++counter;
    		ary.push({});
    		refreshCounter();
    	});

    	setInterval(function() {
    		ary.splice(0);
    		refreshCounter();
    	}, 1000);

    	var tank = $('#tank');
    	$('#swipeArea').on('swipeleft', function() {
    		tank.html((++counter) + ' ぐっど！');
    	});
    }
    
    function refreshCounter() {
    	$('#counter').html(counter);
    	$('#remainder').html(ary.length);
    }

    $(initialize);
})();