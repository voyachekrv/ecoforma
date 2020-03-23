package com.ecoforma.forms;

import com.ecoforma.db.DbSession;
import com.ecoforma.db.mappers.StoreMapper;
import com.ecoforma.entities.ProductView;
import com.ecoforma.entities.Store;
import com.ecoforma.entities.StoreView;
import com.ecoforma.services.CommonActivity;
import com.ecoforma.services.Initializer;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static com.ecoforma.App.COMPANY_NAME;

public class StoreForm {
    JFrame frame;
    private JToolBar toolBar;
    private JButton btnSignOut;
    private JTable tableProduct, tableStore;

    private Initializer initializer;

    private DefaultTableModel initialTableProductModel, initialTableStoreModel;
    private String[] tableProductHeader = new String[] { "Название", "Категория", "Стоимость" };
    private String[] tableStoreHeader = new String[] { "Название", "Количество" };

    private Store currentStore;

    StoreForm(String login, String password) throws IOException {
        initializer = new Initializer();
        CommonActivity activity = new CommonActivity();

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            currentStore = mapper.getStore(login, password);
        }

        frame = initializer.newFrame(COMPANY_NAME + " - " + currentStore.getName(), new Rectangle(323,  144, 1362, 790), JFrame.EXIT_ON_CLOSE);

        toolBar = initializer.newToolBar(0, 0, 1346, 44);
        frame.getContentPane().add(toolBar);

        btnSignOut = initializer.newButtonEnabled("Выход из системы", "icon-logout", new Rectangle(0, 0, 1346, 44));
        toolBar.add(btnSignOut);

        JPanel panelTables = initializer.newPanelDefault(10, 55, 1336, 406);
        frame.add(panelTables);

        JPanel panelTableProduct = initializer.newPanelBevelTable(10, 37, 647, 358);
        panelTables.add(panelTableProduct);

        JPanel panelTableStore = initializer.newPanelBevelTable(670, 37, 647, 358);
        panelTables.add(panelTableStore);

        JLabel lTableProduct = initializer.newLabel("Общий каталог товаров", new Rectangle(10, 11, 647, 20));
        panelTables.add(lTableProduct);

        JLabel lTableStore = initializer.newLabel("Товары на складе", new Rectangle(670, 11, 647, 20));
        panelTables.add(lTableStore);

        tableProduct = initializer.newTable(setInitialTableProductModel());

        JScrollPane tableProductScroll = initializer.newTableScroll(tableProduct, 627, 336);
        panelTableProduct.add(tableProductScroll);

        tableStore = initializer.newTable(setInitialTableStoreModel());

        JScrollPane tableStoreScroll = initializer.newTableScroll(tableStore, 627, 336);
        panelTableStore.add(tableStoreScroll);

        btnSignOut.addActionListener(actionEvent -> activity.signOut(frame));
    }

    private DefaultTableModel setInitialTableProductModel() {
        initialTableProductModel = new DefaultTableModel();
        initialTableProductModel.setColumnIdentifiers(tableProductHeader);

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            ArrayList<ProductView> products = mapper.getProductView();

            for (int i = 0; i < products.size(); i++) {
                initialTableProductModel.insertRow(i, new Object[] {
                        products.get(i).getName(),
                        products.get(i).getCategory(),
                        products.get(i).getCost()
                });
            }
        }

        return initialTableProductModel;
    }

    private DefaultTableModel setInitialTableStoreModel() {
        initialTableStoreModel = new DefaultTableModel();
        initialTableStoreModel.setColumnIdentifiers(tableStoreHeader);

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            ArrayList<StoreView> productsOnThisStore = mapper.getStoreView(currentStore.getID());

            for (int i = 0; i < productsOnThisStore.size(); i++) {
                initialTableStoreModel.insertRow(i, new Object[] {
                        productsOnThisStore.get(i).getName(),
                        productsOnThisStore.get(i).getCount()
                });
            }
        }

        return initialTableStoreModel;
    }
}
