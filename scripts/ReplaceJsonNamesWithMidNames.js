// Reads a Json file exported by MifFileParser, with ids like REGION-n where n is a number.
// Reads a Mid file in CSV format, the mth column (1 = first).
// Replaces REGION-n with the content of the mth column of the nth line (1 = first).
//
// Made to run on the Node.js platform.
// Should accept more encodings, using a conversion library.

var fs = require('fs');
var parseCsvFile = require('./ParseCsvFile');

var arguments = process.argv;

//~ var inputFileName = __dirname + '/config.json';
var inputFileName = arguments[2], fieldNb = arguments[3], inputJson, inputMid, outputFileName, encoding;
if (inputFileName === undefined)
{
	// arguments[0] = "node", arguments[1] = file name
	console.log('Usage: ' + arguments[0] + ' ' + arguments[1] + ' filePath fieldNb [encoding]');
	console.log('filePath without extension, will open filePath_export.json and filePath.mid');
	console.log('fieldNb: number of the CSV field to use (1 = first)');
	return;
}
fieldNb = fieldNb || 1;
encoding = arguments[4] || 'utf8'; // 'ascii' or 'utf8'
outputFileName = inputFileName + '_named.json';
inputJson = inputFileName + '_export.json';
inputMid = inputFileName + '.mid';

var midNames = [];
var options = { fieldIndexes: [ fieldNb - 1 ], readOptions: { encoding: encoding } };
parseCsvFile(inputMid, options,
	function onNext(record)
	{
		midNames.push(record);
	},
	function onComplete()
	{
		console.log('CSV file has been read');

		fs.readFile(inputJson, encoding, function (err, fileContentJson)
		{
			var oi, ti = 0, original, transformed, map = {};

			original = JSON.parse(fileContentJson);
			transformed = { name: original.name, paths: [] };
			for (oi = 0; oi < original.paths.length; oi++)
			{
				var name = midNames[oi][0];
				if (map[name] === undefined)
				{
					map[name] = ti;
					transformed.paths[ti++] = { id: name, path: original.paths[oi].path };
				}
				else
				{
					var index = map[name];
					transformed.paths[index].path += ' ' + original.paths[oi].path;
				}
			}

			fs.writeFile(outputFileName, JSON.stringify(transformed, null, '\t'), function (err)
			{
				if (err)
				{
					console.log(err);
				}
				else
				{
					console.log('JSON saved to "' + outputFileName + '"');
				}
			});
		});
	}
);
