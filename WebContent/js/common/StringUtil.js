function StringUtil() {

}

/**
 * <p>Example</p>
 * <pre>String.format('{0} is dead, but {1} is alive! {0} {2}', 'ASP', 'ASP.NET');</pre>
 * <p>gives</p>
 * <pre>ASP is dead, but ASP.NET is alive! ASP {2}</pre>
 *
 * <p>Reference</p>
 * https://stackoverflow.com/questions/610406/javascript-equivalent-to-printf-string-format
 */
StringUtil.format = function(format) {
    var args = Array.prototype.slice.call(arguments, 1);
    return format.replace(/{(\d+)}/g, function(match, number) { 
        return typeof args[number] != 'undefined' ? args[number] : match;
    });
};
