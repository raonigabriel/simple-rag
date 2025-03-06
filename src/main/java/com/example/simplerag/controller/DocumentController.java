package com.example.simplerag.controller;

import java.util.List;

import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.ai.vectorstore.filter.Filter.Key;
import org.springframework.ai.vectorstore.filter.Filter.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplerag.model.DocumentResponse;
import com.example.simplerag.model.LocaleFilter;
import com.example.simplerag.service.RagService;

import static org.springframework.ai.vectorstore.filter.Filter.ExpressionType.EQ;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentController {

        private final RagService ragService;

        /**
         * Searches for documents similar to the given query.
         *
         * @param query  the search query
         * @param topK   the number of top results to return, defaults to 5 if not
         *               provided
         * @param locale the locale filter to apply to the search results
         * @return a list of DocumentResponse containing the text and score of the
         *         matching documents
         */
        @GetMapping
        public List<DocumentResponse> similaritySearch(@RequestParam(required = true, name = "q") String query,
                        @RequestParam(required = false, name = "k", defaultValue = "5") Integer topK,
                        @RequestParam(required = false, name = "l") LocaleFilter locale) {

                var searchRequest = SearchRequest.builder().query(query).topK(topK);
                if (locale != null) {
                        searchRequest = searchRequest.filterExpression(new Expression(EQ,
                                        new Key(RagService.LOCALE_KEY), new Value(locale.getValue())));
                }

                return ragService.similaritySearch(searchRequest.build());
        }

}
