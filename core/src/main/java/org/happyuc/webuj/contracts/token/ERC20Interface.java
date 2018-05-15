package org.happyuc.webuj.contracts.token;

import org.happyuc.webuj.protocol.core.DefaultBlockParameter;
import org.happyuc.webuj.protocol.core.RemoteCall;
import org.happyuc.webuj.protocol.core.methods.response.RepTransactionReceipt;
import rx.Observable;

import java.math.BigInteger;
import java.util.List;

/**
 * The HappyUC ERC-20 token standard.
 * <p>
 * Implementations should provide the concrete <code>ApprovalEventResponse</code> and
 * <code>TransferEventResponse</code> from their token as the generic types "R" amd "T".
 * </p>
 *
 * @see <a href="https://github.com/happyuc-project/EIPs/blob/master/EIPS/eip-20-token-standard.md">EIPs/EIPS/eip-20-token-standard.md</a>
 * @see <a href="https://github.com/happyuc-project/EIPs/issues/20">ERC: Token standard #20</a>
 */
@SuppressWarnings("unused")
public interface ERC20Interface<R, T> extends ERC20BasicInterface<T> {

    RemoteCall<BigInteger> allowance(String owner, String spender);

    RemoteCall<RepTransactionReceipt> approve(String spender, BigInteger value);

    RemoteCall<RepTransactionReceipt> transferFrom(String from, String to, BigInteger value);

    List<R> getApprovalEvents(RepTransactionReceipt repTransactionReceipt);

    Observable<R> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock);

}
