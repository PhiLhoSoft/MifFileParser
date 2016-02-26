// Parses a CSV file.
//
// Made to run on the Node.js platform.
// Should accept more encodings, using a conversion library.

var fs = require('fs');
var parseCsvFile = require('./ParseCsvFile');

var arguments = process.argv;

var inputFileName = arguments[2], encoding;
if (inputFileName === undefined)
{
	// arguments[0] = "node", arguments[1] = file name
	console.log('Usage: ' + arguments[0] + ' ' + arguments[1] + ' filePath [encoding]');
	return;
}
encoding = arguments[4] || 'utf8'; // 'ascii' or 'utf8'
inputCsv = inputFileName + '.csv';

var options = { readOptions: { encoding: encoding } };
//~ options.hasHeader = true;
//~ options.fieldIndexes = [ 1, 3, 4 ];
parseCsvFile(inputCsv, options,
	function onNext(record)
	{
		console.log(record);
	},
	function onComplete()
	{
		console.log('Done');
	}
);
