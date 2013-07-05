(function() {
    function initialize() {
        var maxToggleX = 372;
        var toggle = $('#toggle');
        var tank = $('#tank');
        new DragManager('#toggle', {
            onDrag: function(e) {
                if (e.diff.x > maxToggleX) return;
                toggle.css('left', e.diff.x);
                setTankNumber(e.diff.x * 100 / maxToggleX);
            },
            onDragEnd: function() {
                toggle.animate({ left: 0 }, 'fast');
                setTankNumber(0);
            }
        });
    }

    function setTankNumber(pNumber) {
        $('#tank').html(Math.ceil(pNumber) + ' ぐっど！');
    }

    function DragManager(pQueryForJQuery, pHandler) {
        var tags = $(pQueryForJQuery);
        var dragStartPosition = null;
        var mouseMoveTarget = $(window);
        tags.on('mousedown', function(e) {
            dragStartPosition = { x: e.pageX, y: e.pageY };
            if (pHandler.onDragStart) pHandler.onDragStart();
            mouseMoveTarget.on('mousemove', function(e) {
                var diff = { x: e.pageX - dragStartPosition.x, y: e.pageY - dragStartPosition.y };
                if (pHandler.onDrag) pHandler.onDrag({ diff: diff });
            });
        });
        $(window).on('mouseup', function() {
            mouseMoveTarget.off('mousemove');
            if (pHandler.onDragEnd) pHandler.onDragEnd();
        });
    }

    $(initialize);
})();
