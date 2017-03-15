SELECT resultsN.Quality, resultsN.order, resultsN.BatchNo, resultsN.TestNo, resultsN.testdate,
resultsN.TestCode, resultsN.Description, resultsN.Name, resultsN.LSL, resultsN.value,
resultsN.USL, resultsN.Check, resultsN.Status
FROM resultsN
WHERE resultsN.Quality='0004720-D' AND resultsN.TestCode='10191' AND resultsN.Name='ML'
ORDER BY resultsN.order, resultsN.BatchNo, resultsN.TestNo




SELECT * from resultsN where Quality='0004720-D'


SELECT  * FROM resultsN
WHERE resultsN.Quality='0004720-D' AND resultsN.TestCode='10191' AND resultsN.Name='ML'
ORDER BY resultsN.order, resultsN.BatchNo, resultsN.TestNo