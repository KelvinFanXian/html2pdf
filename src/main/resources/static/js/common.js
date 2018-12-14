window.onload=function () {
    var queryPairs = location.search.substr(1).split('&');
    queryPairs.forEach(function(t){
        var pair = t.split('='),
        k = pair[0],
        v = pair[1];
        if(v&&v.length>0){
            console.log(k+'--'+v)
            if(document.querySelector('#'+k)){
                document.querySelector('#'+k).value=v;
            } else{
                alert('不存在id为'+k+'的元素！');
            }
        }
    })
}