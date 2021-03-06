package org.sagebionetworks.web.client.widget.entity.renderer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.sagebionetworks.web.client.DisplayConstants;
import org.sagebionetworks.web.client.SynapseJSNIUtils;
import org.sagebionetworks.web.client.widget.entity.editor.APITableColumnConfig;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class APITableColumnRendererDate implements APITableColumnRenderer {

	private DateTimeFormat standardFormatter;
	private SynapseJSNIUtils synapseJSNIUtils;
	private String outputColumnName;
	private Map<String, List<String>> outputColumnData;
	
	@Inject
	public APITableColumnRendererDate(SynapseJSNIUtils synapseJSNIUtils) {
		this.synapseJSNIUtils = synapseJSNIUtils;
		standardFormatter = DateTimeFormat.getFormat(PredefinedFormat.ISO_8601);
	}
	
	@Override
	public void init(Map<String, List<String>> columnData,
			APITableColumnConfig config,
			AsyncCallback<APITableInitializedColumnRenderer> callback) {
		outputColumnName = APITableWidget.getSingleOutputColumnName(config);
		
		//and precompute the output values
		outputColumnData = new HashMap<String, List<String>>();
		String inputColumnName = APITableWidget.getSingleInputColumnName(config);
		List<String> colValues = APITableWidget.getColumnValues(inputColumnName, columnData);
		if (colValues == null) {
			//user defined an input column that doesn't exist in the service output
			callback.onFailure(new IllegalArgumentException(DisplayConstants.ERROR_API_TABLE_RENDERER_MISSING_INPUT_COLUMN + inputColumnName));
			return;
		}
		List<String> outputValues = new ArrayList<String>();
		//assume dates are long values.  if parse exception occurs, then switch to standard formatter parsing (and don't try to parse as long again)
		boolean isMsFromEpoch = true;
		for (Iterator iterator2 = colValues.iterator(); iterator2
				.hasNext();) {
			String colValue = (String) iterator2.next();
			Date date = null;
			try {
				if (isMsFromEpoch)
					date = new Date(Long.parseLong(colValue));
				else
					date = standardFormatter.parse(colValue);	
			} catch (NumberFormatException e) {
				isMsFromEpoch = false;
				date = standardFormatter.parse(colValue);
			}
			outputValues.add(synapseJSNIUtils.convertDateToSmallString(date));
		}
		outputColumnData.put(outputColumnName, outputValues);
		
		callback.onSuccess(new APITableInitializedColumnRenderer() {
			
			@Override
			public Map<String, List<String>> getColumnData() {
				return outputColumnData;
			}
			
			@Override
			public List<String> getColumnNames() {
				return APITableWidget.wrap(outputColumnName);
			}
		});
	}
}
