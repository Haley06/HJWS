function formatDate(value) {
  var formatNum = function(num) {
    return num < 10 ? "0" + num : num;
  };
  var year = value.getFullYear();
  var month = formatNum(value.getMonth() + 1);
  var day = formatNum(value.getDate());
  var hour = formatNum(value.getHours());
  var min = formatNum(value.getMinutes());
  var sec = formatNum(value.getSeconds());
  return year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
}
export default formatDate;
