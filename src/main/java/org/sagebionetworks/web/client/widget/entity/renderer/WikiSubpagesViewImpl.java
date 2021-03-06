package org.sagebionetworks.web.client.widget.entity.renderer;

import java.util.List;

import org.sagebionetworks.web.client.DisplayConstants;
import org.sagebionetworks.web.client.DisplayUtils;
import org.sagebionetworks.web.client.GlobalApplicationState;
import org.sagebionetworks.web.client.utils.AnimationProtector;
import org.sagebionetworks.web.client.utils.AnimationProtectorViewImpl;
import org.sagebionetworks.web.client.utils.UnorderedListPanel;

import com.extjs.gxt.ui.client.Style.Direction;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.FxEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.fx.FxConfig;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class WikiSubpagesViewImpl extends FlowPanel implements WikiSubpagesView {

	private Presenter presenter;
	private GlobalApplicationState globalAppState;
	private static final String SHOW_SUBPAGES_STYLE="col-xs-12 col-md-3";
	private static final String SHOW_SUBPAGES_MD_STYLE="col-md-9";
	private static final String HIDE_SUBPAGES_STYLE="col-xs-12 col-md-12";
	private static final String HIDE_SUBPAGES_MD_STYLE="col-md-12";
	
	private Button showHideButton;
	private LayoutContainer ulContainer;
	private FlowPanel wikiSubpagesContainer;
	private FlowPanel wikiPageContainer;
	
	@Inject
	public WikiSubpagesViewImpl(GlobalApplicationState globalAppState) {
		this.globalAppState = globalAppState;
	}
	
	@Override
	public void configure(TocItem root, FlowPanel wikiSubpagesContainer, FlowPanel wikiPageContainer) {
		clear();
		this.wikiSubpagesContainer = wikiSubpagesContainer;
		this.wikiPageContainer = wikiPageContainer;
		//this widget shows nothing if it doesn't have any pages!
		TocItem mainPage = (TocItem) root.getChild(0);
		if (mainPage.getChildCount() == 0)
			return;
		addStyleName("bs-sidebar");
		//only show the tree if the root has children
		if (mainPage.getChildCount() > 0) {
			//traverse the tree, and create anchors
			final UnorderedListPanel ul = new UnorderedListPanel();
			ul.addStyleName("notopmargin nav bs-sidenav");
			addTreeItemsRecursive(ul, root.getChildren());
			showHideButton = DisplayUtils.createButton("");
			ulContainer = new LayoutContainer();
			ulContainer.setVisible(true);
			ulContainer.add(new HTML("<h4 class=\"margin-left-15\">Pages</h4>"));
			ulContainer.add(ul);

			AnimationProtectorViewImpl protector = new AnimationProtectorViewImpl(showHideButton, ulContainer);
			protector.setContainerVisible(true);
			showSubpages();
			AnimationProtector animation = new AnimationProtector(protector);
			animation.setOutDirection(Direction.LEFT);
			animation.setInDirection(Direction.RIGHT);
			
			FxConfig hideConfig = new FxConfig(300);
			hideConfig.setEffectCompleteListener(new Listener<FxEvent>() {
				@Override
				public void handleEvent(FxEvent be) {
					hideSubpages();
				}
			});
			animation.setHideConfig(hideConfig);
			//already jumpy when showing the hidden subpages (because of the container width change), so make this quick
			FxConfig showConfig = new FxConfig(10);
			showConfig.setEffectCompleteListener(new Listener<FxEvent>() {
				@Override
				public void handleEvent(FxEvent be) {
					showSubpages();
				}
			});
			animation.setShowConfig(showConfig);
			add(ulContainer);
			add(showHideButton);
		}
	}
	
	private void hideSubpages() {
		// This call to layout is necessary to force the scroll bar to appear on page-load
		if (wikiSubpagesContainer != null)
			wikiSubpagesContainer.setStyleName(HIDE_SUBPAGES_STYLE);	
		if (wikiPageContainer != null)
			wikiPageContainer.setStyleName(HIDE_SUBPAGES_MD_STYLE);	
		ulContainer.layout(true);
		showHideButton.setText("Show Pages " + DisplayConstants.RIGHT_ARROWS);
		removeStyleName("well");
		showHideButton.setStyleName("btn btn-default btn-xs left");
	}
	
	private void showSubpages() {
		if (wikiSubpagesContainer != null)
			wikiSubpagesContainer.setStyleName(SHOW_SUBPAGES_STYLE);
		if (wikiPageContainer != null)
			wikiPageContainer.setStyleName(SHOW_SUBPAGES_MD_STYLE);	
		ulContainer.layout(true);
		showHideButton.setText(DisplayConstants.LEFT_ARROWS);
		addStyleName("well");
		showHideButton.setStyleName("btn btn-default btn-xs right");
	}
	
	private void addTreeItemsRecursive(UnorderedListPanel ul, List<ModelData> children) {
		for (ModelData modelData : children) {
			TocItem treeItem = (TocItem)modelData;
			String styleName = treeItem.isCurrentPage() ? "active" : "";
			ul.add(getListItem(treeItem), styleName);
			if (treeItem.getChildCount() > 0){
				UnorderedListPanel subList = new UnorderedListPanel();
				subList.addStyleName("nav");
				subList.setVisible(true);
				ul.add(subList);
				addTreeItemsRecursive(subList, treeItem.getChildren());
			}
		}
	}
	
	private Widget getListItem(final TocItem treeItem) {
		Anchor l = new Anchor(treeItem.getText());
		l.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				globalAppState.getPlaceChanger().goTo(treeItem.getTargetPlace());
			}
		});
		return l;
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}	

	@Override 
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
		
	@Override
	public void showErrorMessage(String message) {
		DisplayUtils.showErrorMessage(message);
	}

	@Override
	public void showLoading() {
	}

	@Override
	public void showInfo(String title, String message) {
		DisplayUtils.showInfo(title, message);
	}
}
