// Parses a CSV file.
// If told it has a header, the callback is called with an object (per line) where the keys are the header names.
// If asked to return field indexes, the callback is called with an array (per line) with the extracted values.
//
// Made to run on the Node.js platform.
// Should accept more encodings, using a conversion library.

var fs = require('fs');

// http://blog.james-carr.org/2010/07/07/parsing-csv-files-with-nodejs/
// https://gist.github.com/PhiLhoSoft/ff60eefcb8ed43326cd7
function parseCsvFile(fileName, options, onNext, onComplete)
{
	var lineNb = 0, header = [], buffer = '';
	var pattern = /(?:^|,)("(?:[^"]*)"|[^,]*)/g;
	var stream = fs.createReadStream(fileName, options.readOptions);
	stream.on('data', function (data) // data is a Buffer
	{
		// Add chunk of data to remainder of previous chunk => the CSV file MUST have a blank line at the end!
		buffer += data.toString();
		// Cut the content of the buffer in lines
		var lines = buffer.split(/[\r\n]+/);
		lines.forEach(function(line, idx)
		{
			// Don't process the last, partial line of this chunk
			if (idx === lines.length - 1) return;
			processLine(line, idx);
		});
		buffer = lines[lines.length - 1];
	});
	stream.on('end', function ()
	{
		// Process the last, complete line of the file (skipped if empty)
		processLine(buffer, 1);
		if (onComplete)
		{
			onComplete();
		}
	});

	function processLine(line, idx)
	{
		if (line === '')
			return; // Skip empty lines

		if (options.hasHeader)
		{
			if (lineNb++ === 0 && idx === 0)
			{
				header = line.split(pattern);
			}
			else
			{
				onNext(buildRecord(line));
			}
		}
		else
		{
			onNext(extractFields(line));
		}
	}

	function buildRecord(line)
	{
		var record = {};
		line.split(pattern).forEach(function (value, index)
		{
			if (header[index] !== '')
			{
				record[header[index]] = value.replace(/"/g, '');
			}
		})
		return record;
	}

	function extractFields(line)
	{
		var fields = [];
		line.split(pattern).forEach(function(value, index)
		{
			if (index % 2 === 0)
				return; // Skip, that's the separator
			index = Math.floor(index / 2);

			var idx;
			if (options.fieldIndexes !== undefined)
			{
				idx = options.fieldIndexes.findIndex(function (v) { return v === index; });
				if (idx === -1)
					return;
			}
			else
			{
				idx = index;
			}

			fields[idx] = value.replace(/"/g, '');
		})
		return fields;
	}
}

module.exports = parseCsvFile;
