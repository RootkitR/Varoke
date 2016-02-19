package ch.rootkit.varoke.communication.events.catalog;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.catalog.VoucherErrorMessageComposer;
import ch.rootkit.varoke.communication.composers.catalog.VoucherValidMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RedeemVoucherMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		String voucher = event.readString();
		if(Varoke.getFactory().getCatalogFactory().checkVoucher(voucher, session.getHabbo()))
		{
			session.sendComposer(new VoucherValidMessageComposer("",""));
		}else{
			session.sendComposer(new VoucherErrorMessageComposer(1));
		}
	}

}
