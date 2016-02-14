$(function(){
    var WS = window['MozWebSocket'] ? window['MozWebSocket'] : WebSocket;
    var socket = new WS('wss://' + window.location.host + '/ws');
    var writeMessages = function(event){
        handleMessage(JSON.parse(event.data));
    }
    socket.onmessage = writeMessages;
    function handleMessage(message) {
        if ($.isEmptyObject(message.result)) {
            $('#respAmount').text(message.responseAmount);
            if ($("#responsesTable tr").length >1 && message.responseAmount==0) {
                location.reload();
            }
        }
        if (!($.isEmptyObject(message.result)) && $('#errorBlock').length) {
            location.reload();
        }
        else {
            $('#respAmount').text(message.responseAmount);
            var html = "<tr>";
            var thAmount = $("#responsesTable th").length;
            $.each(message.result, function(i, n){
                if ($("#responsesTable").length && n.length != thAmount) {
                    location.reload();
                }
                n.forEach(function(item,i,arr) {
                    html+="<td>"+item+"</td>";
                })
            });
            html+="</tr>"
            $("#responsesTable").append(html);
        }
    }
});