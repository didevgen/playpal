$(function(){
    var WS = window['MozWebSocket'] ? window['MozWebSocket'] : WebSocket;
    var socket = new WS('ws://' + window.location.host + '/ws');
    var writeMessages = function(event){
        handleMessage(JSON.parse(event.data));
    }
    socket.onmessage = writeMessages;
    function handleMessage(message) {
        if ($.isEmptyObject(message.result)) {
            $('#respAmount').text(message.responseAmount);
        }
        if (!($.isEmptyObject(message.result)) && $('#errorBlock').length) {
            location.reload();
        }
        else {
            $('#respAmount').text(message.responseAmount);
            var html = "<tr>"
            $.each(message.result, function(i, n){
                n.forEach(function(item,i,arr) {
                    html+="<td>"+item+"</td>";
                })
            });
            html+="</tr>"
            $("#responsesTable").append(html);
        }
    }
});