package org.sagebionetworks.web.client.widget.entity.download;

import org.sagebionetworks.web.client.DisplayConstants;
import org.sagebionetworks.web.client.DisplayUtils;
import org.sagebionetworks.web.client.DisplayUtils.ButtonType;
import org.sagebionetworks.web.client.place.Help;
import org.sagebionetworks.web.client.place.Quiz;
import org.sagebionetworks.web.shared.WebConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class QuizInfoViewImpl extends FlowPanel implements QuizInfoWidgetView {
	private Presenter presenter;
	
	public QuizInfoViewImpl() {
		initializeUI();
	}
	
	private void initializeUI() {
		clear();
		addStyleName("whiteBackground padding-left-10 padding-right-15 padding-top-15");
		HTML html = new HTML("<h5>"+DisplayConstants.QUIZ_INFO+"</h5>" + WebConstants.QUIZ_MORE_INFO);
		html.addStyleName("margin-bottom-40 margin-top-left-10 margin-right-10");
		add(html);
		FlowPanel buttonContainer = new FlowPanel();
		
		com.google.gwt.user.client.ui.Button cancel = DisplayUtils.createButton(DisplayConstants.REMIND_ME_LATER, ButtonType.DEFAULT);
		cancel.addStyleName("right margin-left-10");
		buttonContainer.add(cancel);
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.cancelClicked();
			}
		});
		com.google.gwt.user.client.ui.Button okButton = DisplayUtils.createButton(DisplayConstants.BECOME_CERTIFIED, ButtonType.PRIMARY);
		okButton.addStyleName("right");
		buttonContainer.add(okButton);
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.continueClicked();
				presenter.goTo(new Quiz(WebConstants.USER_CERTIFICATION_TUTORIAL));
			}
		});
		
		add(buttonContainer);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void showLoading() {
	}

	@Override
	public void showInfo(String title, String message) {
	}

	@Override
	public void showErrorMessage(String message) {
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
