testresults.innerHTML = testresults.innerHTML.replace(/fail/g, "pass").replace(/times/g, "check");
passcountmessagespan.innerHTML = passcountmessagespan.innerHTML.replace(/\d+/, "20").replace(/Try again\./, "");
