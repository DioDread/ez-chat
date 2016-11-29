var render = (function () {
    var renderPageReq;

    function renderPage(url, mountPoint) {
        renderPageReq = new Ajax('get', url, {'Accept': 'application/html;'});

        renderPageReq.success = function (data) {
            var scripts = [];
            data.split('\n').forEach(function (line) {
                var script = extractScriptSrc(line);
                if (script) {
                    scripts.push(script);
                }
            });

            scripts.forEach(function (script) {
                mountScript(script);
            });
            mountPoint.innerHTML = data;
        };

        renderPageReq.failure = function (err) {
            console.log(err);
        };

        renderPageReq.call();
    }

    function extractScriptSrc(html) {
        var scriptTagPos = html.indexOf('<script');
        if (scriptTagPos < 0)
            return;

        var scriptCloseTagPos = html.indexOf('>', scriptTagPos);
        var scriptContent = html.substring(scriptTagPos, scriptCloseTagPos);

        var srcStart = scriptContent.indexOf('src=') + 5, srcEnd = scriptContent.indexOf('"', scriptContent.indexOf('src=') + 5);

        return scriptContent.substring(srcStart, srcEnd);
    }

    function mountScript(src) {
        var script = document.createElement('script');
        script.src = src;
        document.body.appendChild(script);
    }

    return renderPage;
}());