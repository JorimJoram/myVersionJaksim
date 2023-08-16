window.onload = function() {
            var ptTypeInputs = document.querySelectorAll('.tType');

            for (var i = 0; i < ptTypeInputs.length; i++) {
                var ptTypeValue = ptTypeInputs[i].value;
                var convertedValue = "";

                switch (ptTypeValue) {
                    case "0":
                        convertedValue = "상담";
                        break;
                    case "1":
                        convertedValue = "1:1";
                        break;
                    case "2":
                        convertedValue = "단체";
                        break;
                }

                ptTypeInputs[i].value = convertedValue;
            }
        }
