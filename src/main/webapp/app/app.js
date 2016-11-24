(function(){
    //TODO Application first laucnh, check user auth, if not call auth render and it's script
    var mountPoint = select('.app-mount-point');
    var renderAuthPage = new Ajax('get', 'http://localhost:8080/ez-chat/auth.html');
    
    renderAuthPage.success = function(data) {
        mountPoint.insertAdjacentHTML('afterbegin', data);
    };
    
    renderAuthPage.failure = function(err) {
        console.log(err);
    };
    
    renderAuthPage.call();
    //TODO After authorization, call render chat, render users and message, register async timers, to check if users sign-in/-out and refresh messages.
    
    //Handle logout event, after logout call auth render, destroy chat and all chat related data;
}());


