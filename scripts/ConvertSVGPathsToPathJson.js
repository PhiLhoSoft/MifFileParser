// Convert a list of SVG paths to a specific Json format (for HighMaps),
// using the SVG path format.
// Made to run on the Node.js platform.
// Should accept more encodings, using a conversion library.
var fs = require('fs');

var arguments = process.argv;

//~ var inputFileName = __dirname + '/config.json';
var inputFileName = arguments[2], outputFileName, encoding;
if (inputFileName === undefined)
{
	console.log('Usage: ' + arguments[0] + ' ' + arguments[1] + ' filePath.svg [encoding]');
	return;
}
encoding = arguments[3] || 'utf8'; // 'ascii' or 'utf8'
if (inputFileName.indexOf('.svg') > 0)
{
	outputFileName = inputFileName.replace('.svg', '.json');
}
else
{
	outputFileName = inputFileName + '.converted.json';
}

fs.readFile(inputFileName, encoding, function (err, fileContent)
{
	if (err)
	{
		console.log('Error: ' + err);
		return;
	}

	var svg, name, type, path, transformed;
	var i, pathCount = 0;

	svg = fileContent.split(/[\r\n]+/);
	transformed = { name: inputFileName.replace(/^.*[\\/]/, ''), paths: [] }

//~ 	console.dir(svg);
	console.log(svg.length);
	for (i = 0; i < svg.length; i++)
	{
		if (svg[i].indexOf('<path') >= 0)
		{
			path = svg[i].replace(/^.*\bd="([^"]+)".*$/, '$1');
			name = svg[i].replace(/^.*\bid="([^"]+)".*$/, '$1');
		}
		if (name !== undefined && path !== undefined)
		{
			transformed.paths[pathCount++] = { id: name, path: path };
			name = path = undefined;
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

