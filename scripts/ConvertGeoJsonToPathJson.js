// Convert a limited / specific subset of GeoJson (the samples I had to convert...) to a specific Json format (for HighMaps),
// using the SVG path format.
// Made to run on the Node.js platform.
// Should accept more encodings, using a conversion library.
var fs = require('fs');

var arguments = process.argv;

//~ var inputFileName = __dirname + '/config.json';
var inputFileName = arguments[2], outputFileName, encoding;
if (inputFileName === undefined)
{
	console.log('Usage: ' + arguments[0] + ' ' + arguments[1] + ' filePath.geo[.]json [encoding]');
	return;
}
encoding = arguments[3] || 'utf8'; // 'ascii' or 'utf8'
if (inputFileName.indexOf('.geo') > 0)
{
	outputFileName = inputFileName.replace(/\.geo\.?json/, '.json');
}
else
{
	outputFileName = inputFileName + '.converted.json';
}

function convertCoordinateListToPath(coordinateList)
{
	path = 'M';
	for (var j = 0; j < coordinateList.length; j++)
	{
		var x = +coordinateList[j][0];
		var y = +coordinateList[j][1];
		path += ' ' + x + "," + -y;
	}
	return path + ' Z';
}

fs.readFile(inputFileName, encoding, function (err, fileContent)
{
	if (err)
	{
		console.log('Error: ' + err);
		return;
	}

	var geojson, name, type, path, transformed;

	geojson = JSON.parse(fileContent);
	transformed = { name: inputFileName.replace(/^.*[\\/]/, ''), paths: [] }

	for (var i = 0; i < geojson.features.length; i++)
	{
		name = geojson.features[i].properties.NAVN;
		if (name === undefined)
		{
			name = 'REGION-' + (i + 1);
		}
		type = geojson.features[i].geometry.type;
		if (type === 'Polygon')
		{
			path = convertCoordinateListToPath(geojson.features[i].geometry.coordinates[0]);
		}
		else if (type === 'MultiPolygon')
		{
			path = '';
			for (var j = 0; j < geojson.features[i].geometry.coordinates.length; j++)
			{
				path += ' ' + convertCoordinateListToPath(geojson.features[i].geometry.coordinates[j][0]);
			}
		}
		else
		{
			console.log('Unknown geometry type: ' + type);
		}
		transformed.paths[i] = { id: name, path: path };
	}
//~   console.dir(geojson);

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

