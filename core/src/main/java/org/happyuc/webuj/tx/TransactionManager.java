package org.happyuc.webuj.tx;

import org.happyuc.webuj.protocol.Webuj;
import org.happyuc.webuj.protocol.core.Request;
import org.happyuc.webuj.protocol.core.methods.response.HucSendRepTransaction;
import org.happyuc.webuj.protocol.core.methods.response.RepTransactionReceipt;
import org.happyuc.webuj.protocol.exceptions.TransactionException;
import org.happyuc.webuj.tx.response.PollingTransactionReceiptProcessor;
import org.happyuc.webuj.tx.response.TransactionReceiptProcessor;

import java.io.IOException;

import static org.happyuc.webuj.protocol.core.JsonRpc2_0Webuj.DEFAULT_BLOCK_TIME;

/**
 * ReqTransaction manager abstraction for executing transactions with HappyUC client via
 * various mechanisms.
 */
public abstract class TransactionManager {

    public static final int DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH = 40;
    public static final long DEFAULT_POLLING_FREQUENCY = DEFAULT_BLOCK_TIME;

    private final TransactionReceiptProcessor transactionReceiptProcessor;
    private final String fromAddress;

    protected TransactionManager(TransactionReceiptProcessor transactionReceiptProcessor, String fromAddress) {
        this.transactionReceiptProcessor = transactionReceiptProcessor;
        this.fromAddress = fromAddress;
    }

    protected TransactionManager(Webuj webu, String fromAddress) {
        this(new PollingTransactionReceiptProcessor(webu, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH), fromAddress);
    }

    protected TransactionManager(Webuj webuj, int attempts, int sleepDuration, String fromAddress) {
        this(new PollingTransactionReceiptProcessor(webuj, sleepDuration, attempts), fromAddress);
    }

    protected RepTransactionReceipt executeTransaction(TransactionData txData) throws IOException, TransactionException {
        Request<?, HucSendRepTransaction> req = makeReqTransaction(txData);
        HucSendRepTransaction hucSendRepTransaction = req.send();
        return processResponse(hucSendRepTransaction);
    }

    public abstract Request<?, HucSendRepTransaction> makeReqTransaction(TransactionData txData) throws IOException;

    public String getFromAddress() {
        return fromAddress;
    }

    private RepTransactionReceipt processResponse(HucSendRepTransaction transactionResponse) throws IOException, TransactionException {
        if (transactionResponse.hasError()) {
            throw new RuntimeException("Error processing transaction request: " + transactionResponse.getError().getMessage());
        }
        String transactionHash = transactionResponse.getTransactionHash();
        return transactionReceiptProcessor.waitForTransactionReceipt(transactionHash);
    }

}
