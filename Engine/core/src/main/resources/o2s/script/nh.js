
// JavaScript to be called using nashorn

function eval(device){
    var result = "Welcome to nashorn!!! dear machine "+ device.getAlias();
    
    print(result);
    return JSON.stringify({result: result, host: device.getHost()});
}