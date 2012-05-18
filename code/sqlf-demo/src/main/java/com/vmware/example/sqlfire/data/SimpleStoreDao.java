package com.vmware.example.sqlfire.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.vmware.example.sqlfire.domain.Order;
import com.vmware.example.sqlfire.domain.OrderItem;
import com.vmware.example.sqlfire.domain.Request;

public class SimpleStoreDao implements StoreData {

	private static final Logger logger = LoggerFactory
			.getLogger(SimpleStoreDao.class);

	private JdbcTemplate dataSource;
	private String dataSourceContext;

	@Override
	public void saveRequest(Request request) {
		logger.debug("Insert: " + request);

		try {

			dataSource
					.update("INSERT INTO "
							+ dataSourceContext
							+ ".REQUESTS (REQUEST_ID,REQUEST_ON,REQUEST_ORDERS,REQUEST_ITEMS,REQUEST_DURATION) VALUES (?,?,?,?,?)",
							new Object[] { request.getId(), request.getOn(),
									request.getOrders(), request.getItems(),
									request.getDuration() });

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateRequestDuration(Request request) {
		logger.debug("Insert: " + request);

		try {

			dataSource
					.update("UPDATE "
							+ dataSourceContext
							+ ".REQUESTS SET REQUEST_ORDERS = ?, REQUEST_ITEMS = ?, REQUEST_DURATION = ? WHERE REQUEST_ID = ?",
							new Object[] { request.getOrders(),
									request.getItems(), request.getDuration(),
									request.getId() });

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public Request getRequest(String id) {
		logger.debug("Request Id: " + id);

		try {

			return dataSource.queryForObject("SELECT * FROM "
					+ dataSourceContext + ".REQUESTS WHERE REQUEST_ID = ?",
					new Object[] { id }, new RowMapper<Request>() {
						public Request mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Request request = new Request();
							request.setId(rs.getString("request_id"));
							request.setOn(rs.getTimestamp("request_on"));
							request.setOrders(rs.getInt("request_orders"));
							request.setItems(rs.getInt("request_items"));
							request.setDuration(rs.getLong("request_duration"));
							return request;
						}
					});

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void saveOder(Request request, final Order order) {
		logger.debug("Insert: " + order);

		try {

			dataSource
					.update("INSERT INTO "
							+ dataSourceContext
							+ ".ORDERS (ORDER_ID,REQUEST_ID,ORDER_ON) VALUES (?,?,?)",
							new Object[] { order.getId(), request.getId(),
									order.getOn() });

			for (OrderItem item : order.getItems()) {

				dataSource
						.update("INSERT INTO "
								+ dataSourceContext
								+ ".ITEMS (ITEM_ID,ORDER_ID,ITEM_NAME,ITEM_PRICE) VALUES (?,?,?,?)",
								new Object[] { item.getId(), order.getId(),
										item.getName(), item.getPrice() });

			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateOrder(Order order) {
		logger.debug("Insert: " + order);

		try {

			dataSource.update("UPDATE " + dataSourceContext
					+ ".ORDERS SET ORDER_ON = ? WHERE ORDER_ID = ?",
					new Object[] { order.getOn(), order.getId() });

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateOrderItem(OrderItem item) {
		logger.debug("Insert: " + item);

		try {

			dataSource.update("UPDATE " + dataSourceContext
					+ ".ITEMS SET ITEM_PRICE = ? WHERE ITEM_ID = ?",
					new Object[] { item.getPrice(), item.getId() });

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void cleanupOrdersAndOderItems() {
		logger.debug("Cleanup");

		try {
			dataSource.execute("DELETE FROM " + dataSourceContext + ".ITEMS");
			dataSource.execute("DELETE FROM " + dataSourceContext + ".ORDERS");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Required
	public void setDataSource(JdbcTemplate dataSource) {
		this.dataSource = dataSource;
	}

	@Required
	public void setDataSourceContext(String dataSourceContext) {
		this.dataSourceContext = dataSourceContext;
	}

}
